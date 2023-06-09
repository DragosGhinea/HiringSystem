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
import CreateApplication from "./pages/CreateApplication";
import {JobApplicationContextProvider} from "./components/shared/JobApplicationContext";
import DeleteJob from "./pages/DeleteJob";
import DeleteApplication from "./pages/DeleteApplication";
import CreateManualCandidate from "./pages/CreateManualCandidate";
import CreateManualInterviewer from "./pages/CreateManualInterviewer";
import CreateManualManager from "./pages/CreateManualManager";
import MyApplicationsPage from "./pages/myApplications/MyApplicationsPage";

function App() {
  return (
    <>
      <AuthContextProvider><JobContextProvider><JobApplicationContextProvider>
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
                  path="/candidate/profile/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CandidateProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviewer/profile/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <InterviewerProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/manager/profile/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <ManagerProfile />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/create"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <CreateJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/edit/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <EditJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/delete/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <DeleteJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/job/get/:id"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <EditJob />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/application/create/:jobId"
                  element={<ProtectedRoute accessBy="authenticated">
                      <CreateApplication />
                  </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/application/delete/:jobApplicationId"
                  element={<ProtectedRoute accessBy="authenticated">
                      <DeleteApplication />
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
              <Route
                  path="/candidate/create"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <CreateManualCandidate />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/interviewer/create"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <CreateManualInterviewer />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/manager/create"
                  element={
                      <ProtectedRoute accessBy="non-authenticated">
                          <CreateManualManager />
                      </ProtectedRoute>
                  }
              ></Route>
              <Route
                  path="/my-applications"
                  element={
                      <ProtectedRoute accessBy="authenticated">
                          <MyApplicationsPage />
                      </ProtectedRoute>
                  }
              ></Route>
          </Routes>
        </Layout>
      </JobApplicationContextProvider></JobContextProvider></AuthContextProvider>
    </>
  );
}

export default App;
