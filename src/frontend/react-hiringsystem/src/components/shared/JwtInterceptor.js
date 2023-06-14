import axios from "axios";
 
const jwtInterceptor = axios.create();

 
jwtInterceptor.interceptors.request.use((config) => {
  let tokensData = JSON.parse(localStorage.getItem("tokens"));
  config.headers["Authorization"] = `Bearer ${tokensData.access_token}`;

  return config;
});
 
jwtInterceptor.interceptors.response.use(
  (response) => {
    return response;
  },
  async (error) => {
    if (error.response.status === 401) {
      const authData = JSON.parse(localStorage.getItem("tokens"));
 
      let apiResponse = await axios.post(
        "http://localhost:8081/api/v1/auth/refresh-token",
        null, {headers: {'Authorization': `Bearer ${authData.refresh_token}`}}
      );

      localStorage.setItem("tokens", JSON.stringify(apiResponse.data));
      error.config.headers[
        "Authorization"
      ] = `Bearer ${apiResponse.data.access_token}`;
      return axios(error.config);
    } else {
      return Promise.reject(error);
    }
  }
);

export default jwtInterceptor;