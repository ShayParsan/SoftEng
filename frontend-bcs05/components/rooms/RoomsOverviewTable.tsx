import { Room } from '@types';
import React from 'react';

type Props = {
  rooms: Room[];
};

const RoomOverviewTable: React.FC<Props> = ({ rooms }) => {
  return (
    <>
      {rooms && (
        <table className="mt-4">
          <thead>
            <tr>
              <th scope="col">Room Name</th>
              <th scope="col">Building</th>
              <th scope="col"></th>
            </tr>
          </thead>
          <tbody>
            {rooms.map((room, index) => (
              <tr key={index}>
                <td>{room.name}</td>
                <td>{room.building.name}</td>
                <td>
                  <button
                    className="text-white bg-blue-700 hover:bg-blue-800 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
                    onClick={() => alert(`Room ${room.name} clicked`)}
                  >
                    Details
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>

        
      )}
    </>
  );
};

export default RoomOverviewTable;
