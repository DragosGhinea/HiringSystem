import { useEffect, useState } from 'react';

const useCountdown = (timeInSeconds) => {
  const [countDown, setCountDown] = useState(timeInSeconds);

  useEffect(() => {
    if(timeInSeconds>0){
        setCountDown(timeInSeconds);

        const interval = setInterval(() => {
            setCountDown(prevCountdown => {
                if(prevCountdown<=1)
                    clearInterval(interval);
                return prevCountdown-1;
            });
        }, 1000);

        return () => clearInterval(interval);
    }
    else{
        setCountDown(0);
    }
  }, [timeInSeconds]);

  return getReturnValues(countDown);
};

const getReturnValues = (countDown) => {
    const totalSeconds = Math.floor(countDown % 60);
    const totalMinutes = Math.floor((countDown / 60) % 60);
    const totalHours = Math.floor((countDown / 3600) % 24);
    const totalDays = Math.floor(countDown / (3600 * 24));

  return [totalDays, totalHours, totalMinutes, totalSeconds];
};

export default useCountdown;