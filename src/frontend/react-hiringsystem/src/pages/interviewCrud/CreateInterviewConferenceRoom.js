import { useState, useRef } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import FormInput from "../../components/forms/FormInput";
import "../css/createInterviewConferenceRoom.css";
import axios from "axios";


const CreateInterviewConferenceRoom = () => {
    const location = useLocation();
    const queryParams = new URLSearchParams(location.search);

    const startDate = queryParams.get('startDate') || "";
    console.log(startDate);

    const [values, setValues] = useState({
        startDate: startDate,
        participants: []
      });

    const email = useRef();
    const participantsError = useRef()

    const navigate = useNavigate();
    
    const inputs = [
    {
        id: 1,
        name: "startDate",
        type: "datetime-local",
        className: 'form-control',
        placeholder: "Start date",
        errorMessage:
        "This field is required!",
        label: "Start Date",
        required: true,
    }
    ];

    const handleSubmit = (e) => {
        e.preventDefault();
        console.log("Data to send")
        axios.post("http://localhost:8081/api/v1/interview/create", values)
            .then(result => {
                navigate(`/interviews/display/${result.data.id}`)
            })
            .catch(err => {
                console.log("Error triggered")
                console.log(err)
            })
    };

    const onChange = (e) => {
        setValues({ ...values, [e.target.name]: e.target.value });
    };

    const addParticipant = async () => {
        const id = await fetchUserId(email.current.value);
        if(id==null){
            participantsError.current.textContent = "User not found!";
            return;
        }

        const existingParticipant = values.participants.find(
            participant => participant.userId === id
        );
        if (existingParticipant) {
            participantsError.current.textContent = "Participant already added!";
            return;
        }

        participantsError.current.textContent = '';

        const newParticipant = {
          userId: id,
          mail: email.current.value,
          isRoomModerator: false
        };

        email.current.value = "";
    
        setValues(prevState => ({
          ...prevState,
          participants: [...prevState.participants, newParticipant],
        }));
    };

    const removeParticipant = (email) => {
        setValues(prevState => ({
            ...prevState,
            participants: prevState.participants.filter(participant => participant.mail !== email)
        }));
    };

    const toggleModerator = (email) => {
        setValues(prevState => ({
          ...prevState,
          participants: prevState.participants.map(participant => {
            if (participant.mail === email) {
              return {
                ...participant,
                isRoomModerator: !participant.isRoomModerator
              };
            }
            return participant;
          })
        }));
    };

    const fetchUserId = async () => {
        try {
          const response = await axios.get(`http://localhost:8081/api/v1/user/id/${email.current.value}`);
          const { id } = response.data;
  
          return id;
        } catch (error) {
          console.error('Error:', error);
          return null;
        }
    };

    return (
        <form onSubmit={handleSubmit}>
        <h1>Schedule Interview</h1>
        {inputs.map((input) => (
            <FormInput
            key={input.id}
            {...input}
            value={values[input.name]}
            onChange={onChange}
            />
        ))}
        <br />

        <div className="card participants">
            <div className="card-body">
                <h5 className="card-title">Participants</h5>
                <ul className="list-group mb-4">
                    {
                        values.participants.map((participant, index) => (
                            <li key={index} className="list-group-item d-flex justify-content-between align-items-center">
                                <div>
                                    {participant.mail}
                                </div>
                                <div className="btn-group">
                                    <div
                                        className={`btn btn-${participant.isRoomModerator ? 'success' : 'info'}`}
                                        onClick={() => toggleModerator(participant.mail)}
                                    >
                                        {participant.isRoomModerator ? 'Moderator' : 'Participant'}
                                    </div>
                                    <div
                                        className="btn btn-outline-danger"
                                        onClick={() => removeParticipant(participant.mail)}
                                    >
                                    Remove
                                    </div>
                                </div>
                            </li>
                        ))
                    }
                </ul>
                <div className="input-group mb-3">
                    <input type="text" ref={email} className="form-control" placeholder="Enter participant email" />
                    <div onClick={addParticipant} className="btn btn-primary">Add Participant</div>
                </div>
                <span className="error" ref={participantsError}></span>
            </div>
        </div>

        <br />
        <button className="btn btn-success" type="submit">Submit</button>
        </form>
    );
}

export default CreateInterviewConferenceRoom;