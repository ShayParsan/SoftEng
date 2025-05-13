import Head from 'next/head';
import { useEffect, useState } from 'react';
import Header from '@components/header';
import ScheduleOverview from '@components/schedule/ScheduleOverview';
import ScheduleService from '@services/ScheduleService';
import StudentService from '@services/StudentService';
import RoomServices from '@services/RoomServices';
import { Schedule, Student, User, Room } from '@types';
import useInterval from 'use-interval';
import { serverSideTranslations } from 'next-i18next/serverSideTranslations';

const Schedules: React.FC = () => {
  const [schedules, setSchedules] = useState<Array<Schedule>>();
  const [students, setStudents] = useState<Array<Student>>([]);
  const [rooms, setRooms] = useState<Array<Room>>([]);
  const [error, setError] = useState<string>();
  const [loggedInUser, setLoggedInUser] = useState<User | null>(null);
  

  useEffect(() => {
    const loggedInUserString = sessionStorage.getItem('loggedInUser');
    if (loggedInUserString !== null) {
      setLoggedInUser(JSON.parse(loggedInUserString));
    }
  }, []);

  const getSchedulesAndStudents = async () => {
    setError('');
    const responses = await Promise.all([
      ScheduleService.getSchedule(),
      StudentService.getAllStudents(),
      RoomServices.getAllRooms()
    ]);

    

    

    

    const [schedulesResponse, studentsResponse, roomsResponse] = responses;

    if (!schedulesResponse.ok || !studentsResponse.ok || !roomsResponse.ok) {
      if (schedulesResponse.status === 401) {
        setError('You are not authorized to view this page. Please login first.');
      } else if (schedulesResponse.status === 403) {
        setError('Access denied. Only admins and lecturers can view schedules.');
      } else {
        setError(schedulesResponse.statusText);
      }
    } else {
      const schedules = await schedulesResponse.json();
      const students = await studentsResponse.json();
      const rooms = await roomsResponse.json();
      setSchedules(schedules);
      setStudents(students);
      setRooms(rooms);
    }
  };

  useEffect(() => {
    getSchedulesAndStudents();
  }, []);

  useInterval(() => {
    if (!error) getSchedulesAndStudents();
  }, 1000);

  return (
    <>
      <Head>
        <title>Schedules</title>
      </Head>
      <Header />
      <main className="p-6 min-h-screen flex flex-col items-center">
        <h1>
          Schedule{' '}
          {loggedInUser &&
            `for ${
              loggedInUser.role === 'admin'
                ? 'all users (admin)'
                : loggedInUser.fullname
            }`}
        </h1>
        <>
          {error && <div className="text-red-800">{error}</div>}
          {schedules && (
            <ScheduleOverview schedules={schedules} students={students} rooms={rooms}/>
          )}
        </>
      </main>
    </>
  );
};

export const getServerSideProps = async (context: { locale: any }) => {
  const { locale } = context;

  return {
    props: {
      ...(await serverSideTranslations(locale ?? 'en', ['common'])),
    },
  };
};

export default Schedules;
