import React, {Component} from 'react';
import {Building} from "./Building";
//import 'react-dropdown/style.css';
import {Edge} from "./Line";
import {Point} from "./Point";

interface BuildingListProps {
    onChange(edges: Edge[], points: Point[]): void;
}

interface BuildingListState {
    buildings: Building[];
    startBuilding: string;
    endBuilding: string;
}

/**
 * Two dropdown menus that will allow a user to select the start and end buildings
 * and view the shortest path between the buildings. Additional buttons are included:
 * find path and reset
 */
class MapBuilding extends Component<BuildingListProps, BuildingListState> {
    constructor(props: any) {
        super(props);
        this.state = {
            buildings: [],
            startBuilding: "",
            endBuilding: "",
        };
    }

    /**
     * Retrieves a list of buildings from the given string
     * @param str string data parsed to get list of buildings
     */
    parseBuildings(str: string) {
        //removes curly brackets
        str = str.substring(1, str.length - 1);

        //splits the given string by commas
        let data: string[] = str.split(",");

        let buildingList: Building[] = [];

        for (let i = 0; i < data.length; i++) {

            let oneBuilding: string[] = data[i].split(":");
            let short = oneBuilding[0].substring(1, oneBuilding[0].length - 1);
            let long = oneBuilding[1].substring(1, oneBuilding[1].length - 1);
            buildingList.push({shortName: short, longName: long, key: short + ":" + long});
            console.log(short + " " + long);
        }

        // Comparator to handle sorting of buildings in alphabetical order
        const sortByName = (a: Building, b: Building) => {
            const aName = a.longName.toLowerCase();
            const bName = b.longName.toLowerCase();
            return aName.localeCompare(bName);
        }

        buildingList.sort(sortByName);
        this.setState({
            buildings: buildingList,
        })
    }

    /**
     * Retrieves and stores the shortest path between two buildings from the given string
     * into an array of Edges and stores the x and y coordinates of thestart and end buildings
     * in an array of Points
     * @param str the data that is being parsed to determine the path
     */
    parsePath(str: string) {
        str = str.substring(2, str.length - 2);
        let data: string[] = str.split("},{");
        let edgeList: Edge[] = [];
        let pointList: Point[] = [];
        for (let i = 0; i < data.length; i++) {
            let oneEdge: string[] = data[i].split(",");
            let color: string = 'purple';
            for (let j = 0; j < oneEdge.length; j++) {
                oneEdge[j] = oneEdge[j].substring(5);
            }
            edgeList.push({x1: parseInt(oneEdge[0]), y1: parseInt(oneEdge[1]),
                x2: parseInt(oneEdge[2]), y2: parseInt(oneEdge[3]), color: color, key: i.toString()});
        }
        //gets the start point of the path
        let startPoint: Point = {
            x1: edgeList[0].x1,
            y1: edgeList[0].y1,
            key: edgeList[0].x1 + "," + edgeList[0].y1
        };
        //gets the end point of the path
        let endPoint: Point = {
            x1: edgeList[edgeList.length - 1].x1,
            y1: edgeList[edgeList.length - 1].y1,
            key: edgeList[0].x1 + "," + edgeList[0].y1
        }
        pointList.push(startPoint, endPoint);
        this.props.onChange(edgeList, pointList);
    }

    //sets the state of the start building
    setStart(evt: any) {
        this.setState({
            startBuilding: evt.target.value,
        });
    }

    //sets the state of the end building
    setEnd(evt: any) {
        this.setState({
            endBuilding: evt.target.value,
        });
    }

    //clears all displayed points and lines and resets the start and end
    //buildings to their initial state
    clear() {
        this.setState({
            startBuilding:"",
            endBuilding:"",
        })
        this.props.onChange([], []);
    }

    // parses buildings into a dropdown list
    getBuildings() {
        return this.state.buildings.map((building, index) => {
            return (
                <option key={index} value={building.shortName}>{building.longName}</option>
            )
        })
    }

    // retrieves and parses buildings from server
    componentDidMount = async () => {
        try {
            let response = await fetch("http://localhost:4567/get-buildings");
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }
            let parsingPromise = await response.text();
            this.parseBuildings(parsingPromise);

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    //retrieves and parses the path between the start and end buildings selected by the user
    //from the dropdown menus through the spark server
    // alerts for when responses are incomplete; start and end buildings are not entered
    // alert for when the start and end buildings are the same
    getPath = async () => {
        const start = this.state.startBuilding;
        const end = this.state.endBuilding;
        //alerts user if start and end buildings have not been selected
        if (start === "" && end === "") {
            alert("Please select both start and end buildings!");
            return;
        }
        //alerts user if start and end buildings are identical
        else if (start === end) {
            alert("Start and end buildings cannot be the same!");
            return;
        }
        //alerts user if start building has not been selected
        else if (start === "") {
            alert("Please select a start building!");
            return;
        }
        //alerts user if end building has not been selected
        else if (end === "") {
            alert("Please select an end building!");
            return;
        }

        try {
            const url = `http://localhost:4567/find-path?start=${start}&end=${end}`;
            let response = await fetch(url);
            if (!response.ok) {
                alert("The status is wrong! Expected: 200, Was: " + response.status);
                return; // Don't keep trying to execute if the response is bad.
            }
            let text = await response.text();
            this.parsePath(text);

        } catch (e) {
            alert("There was an error contacting the server.");
            console.log(e);
        }
    };

    render() {
        return (
            <div>

                {/*First dropdown menu to select starting building*/}
                <h3> Start Building </h3>
                <div id="dropdown">
                    <select onChange={this.setStart.bind(this)} name="start" id="dropdown-list" >
                        <option disabled selected> --- Select a start building --- </option>
                        {this.getBuildings()}
                    </select>
                </div>

                {/*Second dropdown menu to select starting building*/}
                <h3> End Building </h3>
                <div id="dropdown">
                    <select onChange={this.setEnd.bind(this)} name="end" id="dropdown-list">
                        <option disabled selected> --- Select an end building --- </option>
                        {this.getBuildings()}
                    </select>
                </div>
                <div id = "button-container">
                    {/*Buttons*/}
                    <div>
                        <button className = "find-path" onClick={() => this.getPath()}><h2>Find Path</h2></button>
                    </div>

                    <div>
                        <button className = "reset" onClick={() => this.clear()}><h2>Reset</h2></button>
                    </div>
                </div>


            </div>
        );
    }
}

export default MapBuilding;