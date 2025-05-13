import classNames from 'classnames';
import dayjs from 'dayjs';
import React, { useState } from 'react';
import ScheduleService from '@services/ScheduleService';
import RoomServices from '@services/RoomServices';
import { Schedule, Student, Room } from '@types';


type Props = {
  schedules: Array<Schedule>;
  students: Array<Student>;
  rooms: Array<Room>;
};

const ScheduleOverview: React.FC<Props> = ({ schedules, students, rooms }: Props) => {
  const [selectedSchedule, setSelectedSchedule] = useState<Schedule | null>(
    null
  );

  
  const [roomError, setRoomError] = useState<string | null>(null);

  const selectSchedule = (schedule: Schedule) => {
    setSelectedSchedule(schedule);
  };

  const handleEnroll = async (student: Student) => {
    if (!selectedSchedule) return;

    await ScheduleService.enrollStudent(selectedSchedule, student);
    selectedSchedule.students.push(student);

    
  };

  const handleAssignRoom = async (room: Room) => {
    if (!selectedSchedule || selectedSchedule.id === undefined) return;
  
    const response = await ScheduleService.assignRoom(selectedSchedule.id, room.id);
  
    if (response.ok) {
      //alert(`Room "${room.name}" successfully assigned to schedule.`);
      selectedSchedule.room = room; 
      setRoomError(null);
    } else {
      const errorData = await response.json();
      setRoomError('Failed to assign room, schedule already exists for this time and room');
    }
  };

  

  return (
    <>
    {roomError && (
      <div className="text-red-600 text-center mb-4">
        {roomError}
      </div>
    )}
    
      {schedules && (
        <table className="mt-6">
          <thead>
            <tr>
              <th scope="col">Course</th>
              <th scope="col">Start</th>
              <th scope="col">End</th>
              <th scope="col">Lecturer</th>
              <th scope="col">Enrolled students</th>
              <th scope="col">Room</th>
            </tr>
          </thead>
          <tbody>
            {schedules.map((schedule, index) => (
              <tr
                key={index}
                onClick={() => selectSchedule(schedule)}
                className={classNames({
                  'table-active': selectedSchedule?.id === schedule.id,
                })}
                role="button">
                <td>{schedule.course.name}</td>
                <td>{dayjs(schedule.start).format('DD-MM-YYYY HH:mm')}</td>
                <td>{dayjs(schedule.end).format('DD-MM-YYYY HH:mm')}</td>
                <td>
                  {schedule.lecturer.user.firstName +
                    ' ' +
                    schedule.lecturer.user.lastName}
                </td>
                <td>{schedule.students.length} </td>
                <td>{schedule.room?.name ?? 'Unassigned'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
      {selectedSchedule && (
        <section className="mt-5">
          <h2 className="text-center">Students</h2>
          <table>
            <thead>
              <tr>
                <th scope="col">Firstname</th>
                <th scope="col">Lastname</th>
                <th scope="col">Studentnumber</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
              {students.map((student, index) => (
                <tr key={index}>
                  <td>{student.user.firstName}</td>
                  <td>{student.user.lastName}</td>
                  <td>{student.studentnumber}</td>
                  <td>
                    {!selectedSchedule.students.find(
                      (s) => s.studentnumber === student.studentnumber
                    ) && (
                      <button
                        className="text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                        onClick={() => handleEnroll(student)}>
                        Enroll
                      </button>
                    )}
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </section>
      )}
      {selectedSchedule && (
        <section className="mt-5">
          <h2 className="text-center">Available Rooms</h2>
          <table>
            <thead>
              <tr>
                <th scope="col">Room Name</th>
                <th scope="col">Building</th>
                <th></th>
              </tr>
            </thead>
            <tbody>
            {rooms.map((room, index) => (
            <tr key={index}>
              <td>{room.name}</td>
              <td>{room.building.name}</td>
              <td>
                {!selectedSchedule?.room && (
                  <button
                    className="text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                    onClick={() => handleAssignRoom(room)}
                  >
                    Book
                  </button>
                )}
    </td>
  </tr>
))}
            </tbody>
          </table>
        </section>
      )}
    </>
  );
};

export default ScheduleOverview;
