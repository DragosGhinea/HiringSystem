import React, { useEffect } from 'react';
import { useCallback } from "react";
import Particles from "react-tsparticles";
import { loadFull } from "tsparticles";

const MainPageParticles = () => {

    const particlesInit = useCallback(async engine => {
        //console.log(engine);
        // you can initiate the tsParticles instance (engine) here, adding custom shapes or presets
        // this loads the tsparticles package bundle, it's the easiest method for getting everything ready
        // starting from v2 you can add only the features you need reducing the bundle size
        await loadFull(engine);
    }, []);

    const particlesLoaded = useCallback(async container => {
        //await console.log(container);
    }, []);

    return (
        <Particles
            id="tsparticles"
            init={particlesInit}
            loaded={particlesLoaded}
            options={{
                background: {
                    color: {
                        value: "transparent",
                    },
                },
                "fps_limit": 60,
    "interactivity": {
        "detectsOn": "canvas",
        "events": {
            "onClick": {
                "enable": true,
                "mode": "push"
            },
            "onHover": {
                "enable": true,
                "mode": "repulse"
            },
            "resize": true
        },
        "modes": {
            "push": {
                "particles_nb": 4
            },
            "repulse": {
                "distance": 200,
                "duration": 0.4
            }
        }
    },
    "particles": {
        "color": {
            "value": "#fff"
        },
        "links": {
            "color": "#fff",
            "distance": 150,
            "enable": true,
            "opacity": 0.4,
            "width": 1
        },
        "move": {
            "bounce": false,
            "direction": "none",
            "enable": true,
            "outMode": "out",
            "random": false,
            "speed": 2,
            "straight": false
        },
        "number": {
            "density": {
                "enable": true,
                "area": 800
            },
            "value": 80
        },
        "opacity": {
            "value": 0.5
        },
        "shape": {
            "type": "circle"
        },
        "size": {
            "random": true,
            "value": 5
        }
    },
    "detectRetina": true
            }}
        />
    )
};

export default MainPageParticles;