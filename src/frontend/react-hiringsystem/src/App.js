import "./App.css";
import Layout from "./components/shared/Layout";
import { Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import { AuthContextProvider } from "./components/shared/AuthContext";
import { JobContextProvider } from "./components/shared/JobContext";
import ProtectedRoute from "./components/shared/ProtectedRoute";
import MainPage from "./pages/MainPage";
import Register from "./pages/Register";
import CandidateProfile from "./pages/CandidateProfile";
import InterviewerProfile from "./pages/InterviewerProfile";
import ManagerProfile from "./pages/ManagerProfile";
import CreateJob from "./pages/CreateJob";
import EditJob from "./pages/EditJob";
import CreateInterviewConferenceRoom from "./pages/interviewCrud/CreateInterviewConferenceRoom";
import DisplayInterviewConferenceRoom from "./pages/interviewCrud/DisplayInterviewConferenceRoom";
import EditInterviewConferenceRoom from "./pages/interviewCrud/EditInterviewConferenceRoom";
import InterviewRoomPage from "./pages/interviewRoom/InterviewRoomPage";
import InterviewLeft from "./pages/interviewRoom/InterviewLeft";
import InterviewWaitingRoom from "./pages/interviewRoom/InterviewWaitingRoom";

function App() {
  return (
    <>
      <AuthContextProvider><JobContextProvider>
        <Layout>
          <Routes>
            <Route path="/" element={<MainPage />}></Route>
 
            <Route
              path="/login"
              element={
                <ProtectedRoute accessBy="non-authenticated">
                  <Login />
                </ProtectedRoute>
              }
            ></Route>
            <Route
              path="/register"
              element={
                <ProtectedRoute accessBy="non-authenticated">
                  <Register />
                </ProtectedRoute>
              }
            ></Route>
              <Route
                  path="/candidate/profile"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <CandidateProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviewer/profile"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <InterviewerProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/manager/profile"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <ManagerProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/create"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <CreateJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/edit/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <EditJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/delete/:id"
                  element={<MainPage />}
              ></Route>
              <Route
                  path="/job/get/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <EditJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/create"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <CreateInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/display/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <DisplayInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/edit/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <EditInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/delete/:id"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <EditInterviewConferenceRoom />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interview/room/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <InterviewWaitingRoom />
                      </ProtectedRoute>
                  }
              ></Route>

                <Route
                  path="/interview/room/left"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <InterviewLeft />
                      </ProtectedRoute>
                  }
              ></Route>
          </Routes>
        </Layout>
      </JobContextProvider></AuthContextProvider>
    </>
  );
}

export default App;
