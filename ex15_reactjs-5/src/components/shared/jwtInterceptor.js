import axios from 'axios';

const jwtInterceptor = axios.create({});

jwtInterceptor.interceptors.request.use((config) => {
  let tokenData = JSON.parse(localStorage.getItem('tokens'));
  config.headers['Authorization'] = `Bearer ${tokenData}`;
  return config;
});

export default jwtInterceptor;
