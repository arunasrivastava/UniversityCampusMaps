/*
 * Copyright (C) 2023 Soham Pardeshi.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

import React, { Component } from "react";
import BuildingList from "./MapBuilding";
import Map from "./Map";
import {Edge} from "./Line";
import {Point} from "./Point";

// Allows us to write CSS styles inside App.css, any styles will apply to all components inside <App />
import "./App.css";

interface AppState {
    lines: Edge[];
    points: Point[];
    isCardOpen: boolean;
}
// Component consisting of lines, points, and card containing user inputs
class App extends Component<{}, AppState> {
    constructor(props: any) {
        super(props);
        this.state = {
            lines: [],
            points: [],
            isCardOpen: true, // Initialize card as open
        };
    }
// set line takes array of edges
    setLines(value: Edge[]) {
        this.setState({ lines: value });
    }
// set points takes array of points

    setPoints(value: Point[]) {
        this.setState({ points: value });
    }
// toggle card to open and close card
    toggleCard = () => {
        this.setState(prevState => ({
            isCardOpen: !prevState.isCardOpen,
        }));
    };
// render function builds with toggle off and card open. Returns the map with lines and points drawn
    render() {
        const { isCardOpen } = this.state;

        const cardContent = isCardOpen && (
            <div id="card">
                <h1 id="app-title">UW Campus Maps</h1>
                <BuildingList
                    onChange={(edges, points) => {
                        this.setLines(edges);
                        this.setPoints(points);
                    }}
                />
            </div>
        );

        return (
            <div id="uw-mapper">
                <button className="toggle-button" onClick={this.toggleCard}>
                    {isCardOpen ? "x" : "+"}
                </button>
                {cardContent}

                <div>
                    <Map
                        drawEdges={this.state.lines}
                        drawPoints={this.state.points}
                    />
                </div>
            </div>
        );
    }
}

export default App;