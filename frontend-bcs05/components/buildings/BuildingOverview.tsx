import React from "react";
import { Building } from "@types";
import dayjs from "dayjs";

type Props = {
  buildings: Building[];
};

const BuildingOverview: React.FC<Props> = ({ buildings }) => {
  return (
    <div className="mt-6">
      {buildings.map((building) => (
        <div key={building.id} className="mb-6">
          <h2 className="text-xl font-bold mb-2">{building.name}</h2>
          {building.rooms.length === 0 ? (
            <p>No rooms in this building.</p>
          ) : (
            <table className="w-full border mb-4">
              <thead>
                <tr>
                  <th>Room</th>
                  <th>Schedules</th>
                </tr>
              </thead>
              <tbody>
                {building.rooms.map((room) => (
                  <tr key={room.id}>
                    <td className="border px-4 py-2">{room.name}</td>
                    <td className="border px-4 py-2">
                      {room.schedules.length === 0 ? (
                        "No schedules"
                      ) : (
                        <ul>
                          {room.schedules.map((schedule) => (
                            <li key={schedule.id}>
                              {dayjs(schedule.start).format("DD-MM-YYYY HH:mm")} –{" "}
                              {dayjs(schedule.end).format("DD-MM-YYYY HH:mm")}
                              
                            </li>
                          ))}
                        </ul>
                      )}
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      ))}
    </div>
  );
};

export default BuildingOverview;
