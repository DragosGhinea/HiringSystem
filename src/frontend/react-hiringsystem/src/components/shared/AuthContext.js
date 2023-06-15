import axios from "axios";
import { createContext, useState } from "react";
import jwt_decode from "jwt-decode";
import { useNavigate } from "react-router-dom";
import jwtInterceptor from "./JwtInterceptor";

const AuthContext = createContext();
 
export const AuthContextProvider = ({ children }) => {
  const [user, setUser] = useState(() => {
    if (localStorage.getItem("tokens")) {
      let tokens = JSON.parse(localStorage.getItem("tokens"));
      const decoded = jwt_decode(tokens.access_token);

      return {
        email: decoded.sub,
        userType: decoded.userType
      };
    }
    return null;
  });
 
  const navigate = useNavigate();

  const register = async (payload) => {
    await axios.post(
      "http://localhost:8081/api/v1/auth/register",
      payload
    );

    navigate("/register/sent");
  };
 
  const login = async (payload) => {
    return await axios.post(
      "http://localhost:8081/api/v1/auth/authenticate",
      payload
    ).then(apiResponse => {
      localStorage.setItem("tokens", JSON.stringify(apiResponse.data));
      const decoded = jwt_decode(apiResponse.data.access_token);
      const userData = {
        email: payload.email,
        userType: decoded.userType
      }
      setUser(userData);
      navigate("/");
    }).catch(error => {
      if(error.response.data.message){
        return {
          errorMessage: error.response.data.message
        }
      }
      else{
        return {
          errorMessage: "An error has occured."
        }
      }
    });
  };

  const logout = async () => {
    await jwtInterceptor.post("http://localhost:8081/api/v1/auth/logout");
 
    localStorage.removeItem("tokens");
    setUser(null);
    navigate("/");
  };

  return (
    <AuthContext.Provider value={{ user, register, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
};
 
export default AuthContext;