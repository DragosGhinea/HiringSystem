import "./CountdownBox.css"

const CountdownBox = ({title, days, hours, minutes, seconds}) => {

    return (
        <div className="countdown-parent-box">
            <h4>{title}</h4>
            <div className="countdown-box">
                <div className="days">
                    <p>{days}</p>
                    <span>{days===1?"Day":"Days"}</span>
                </div>
                <div className="hours">
                    <p>{hours}</p>
                    <span>{hours===1?"Hour":"Hours"}</span>
                </div>
                <div className="minutes">
                    <p>{minutes}</p>
                    <span>{minutes===1?"Minute":"Minutes"}</span>
                </div>
                <div className="seconds">
                    <p>{seconds}</p>
                    <span>{seconds===1?"Second":"Seconds"}</span>
                </div>
            </div>
        </div>
    );
}

export default CountdownBox;