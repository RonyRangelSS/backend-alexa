import axios from 'axios';

const baseURL = 'https://cartoes-rapidos-64cbd3337889.herokuapp.com/';

const api = axios.create({
  baseURL,
});

export default api;