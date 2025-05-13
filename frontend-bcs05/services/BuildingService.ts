const getToken = (): string => {
    const loggedInUserString = sessionStorage.getItem('loggedInUser');
    return loggedInUserString ? JSON.parse(loggedInUserString).token : '';
  };

  const getAllBuildings = async () => {
    return fetch(process.env.NEXT_PUBLIC_API_URL + '/buildings', {
        method: 'GET',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${getToken()}`,
        },
      });
  };

  export default {
    getAllBuildings
  };