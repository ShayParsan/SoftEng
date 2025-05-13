const getToken = (): string => {
    const loggedInUserString = sessionStorage.getItem('loggedInUser');
    return loggedInUserString ? JSON.parse(loggedInUserString).token : '';
  };

const getAllRooms = async () => {
    return fetch(process.env.NEXT_PUBLIC_API_URL + '/rooms', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${getToken()}`,
        },
      });
  };

  const getRoomById = (RoomId: string) => {
    return fetch(process.env.NEXT_PUBLIC_API_URL + `/rooms/${RoomId}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${getToken()}`,
      },
    });
  };
  
  
  export default {
    getAllRooms,
    getRoomById
  };