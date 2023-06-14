import React, { useEffect, useState } from "react";
import { Calendar, momentLocalizer } from "react-big-calendar";
import format from "date-fns/format";
import moment from "moment";
import "react-big-calendar/lib/css/react-big-calendar.css";
import { useNavigate } from "react-router-dom";

moment.locale("en-GB");
const localizer = momentLocalizer(moment);

const InterviewsCalendar = ({interviews}) => {
  const [interviewsData, setInterviewsData] = useState();
  const navigate = useNavigate();

  const EventComponent = ({ event }) => (
    <div>
      <div>{event.title}</div>
      <div>Participants: {event.participants}</div>
    </div>
  );

  useEffect(() => {
    let toSet = []
    console.log(interviews)
    interviews.forEach(interview => {
        const date = new Date(interview.startDate);
        const dateEnd = new Date(date.getTime());
        dateEnd.setMinutes(dateEnd.getMinutes()+30);
        const startDate = format(date, 'HH:mm (z)');
        toSet.push({
            id: interview.id,
            start: date,
            end: dateEnd,
            title: `Interview scheduled ${startDate}`,
            participants: interview.participants.length
        });
    })
    setInterviewsData(toSet);
  }, []);

  const handleSelect = ({ start, end }) => {
      navigate(`/interviews/create?startDate=${start.toISOString().slice(0, 16)}`)
  };

  const onSelectEvent = (event) => {
      navigate(`/interviews/display/${event.id}`)
  }

  return (
    <div className="App">
      <Calendar
        views={["day", "agenda", "work_week", "month"]}
        selectable
        localizer={localizer}
        defaultDate={new Date()}
        defaultView="month"
        events={interviewsData}
        components={{
          event: EventComponent, // Custom event component
        }}
        style={{ height: "90vh" }}
        onSelectEvent={onSelectEvent}
        onSelectSlot={handleSelect}
      />
    </div>
  );
}

export default InterviewsCalendar;