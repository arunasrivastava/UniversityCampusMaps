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

import React, {Component} from 'react';

// Callback to update the edge list in the parent component
interface EdgeListProps {
    onChange(edges: Edge[] | null): void;

}
// Holds the input text value for edge list
interface EdgeListState {
    val: string;
}
//represents a single edge with coordinates (x1, y1), (x2,y2) and color
type Edge = {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
    color: string;
}


/**
 * A text field that allows the user to enter the list of edges.
 * Also contains the buttons that the user will use to interact with the app.
 */

class EdgeList extends Component<EdgeListProps, EdgeListState> {

    constructor(props: EdgeListProps) {
        super(props);
        this.state = {
            val: "",
        }

    }
    // Clears the input and updates the parent component's edge list
    onClearClick(){
        // state to store the current contents of the text box to be used with onChange
        this.setState({
            val: '',
        })
        this.props.onChange(null); // clearing edge list
    }
    // Validates the input and updates the parent component's edge list
    onDrawClick() {
        // Draw on click triggers contents of map replaced by whatever is currently in the edge lisst
        let edges = this.validateEdge(this.state.val);
        if (edges === null || edges.length === 0){
            alert("Invalid input\ne.g) 100 100 1400 2500 red\n");
            this.onClearClick();
            console.log("Draw Failed");
        } else {
            this.props.onChange(edges);
            console.log("Draw Success");
        }
    }
    // Validates the input text and extracts edges if valid, parsing before sending to App
    // Assumes that string input is well-formatted and spells out a reasonable color
    validateEdge(val: string): Edge[] | null {
        let ret: Edge[] = [];

        let lines = val.split("\n");
        for (let i = 0; i < lines.length; i++) {
            let elems = lines[i].trim().split(" ");
            if (elems.length < 5)
                return null;

            let flag: boolean = true;
            for (let j = 0; j < 4; j++){
                // Minimum of (0,0) near intersection of Latona AV. and maximum of (4000,4000) near 2340 Broadmoor Dr. E should not be exceeded
                if (parseInt(elems[j]) < 0 || parseInt(elems[j]) > 4000){
                    flag = false;
                    alert('Invalid input: coordinates must be at least 0 and up to 4,000')
                }
            }

            // parse data such that parsed data can be handed up to the App
            if (flag){
                let edge: Edge = {
                    x1: parseInt(elems[0]),
                    y1: parseInt(elems[1]),
                    x2: parseInt(elems[2]),
                    y2: parseInt(elems[3]),
                    color: elems[4]
                }

                ret.push(edge);
            } else {
                return null;
            }
        }

        return ret;
    }
    // render function with text area for user-input, edge data, and a button to trigger drawing
    // of the edges.
    render() {
        return (
            <div id="edge-list">
                Edges <br/>
                <textarea
                    rows={5}
                    cols={30}
                    onChange={(e) => {
                        this.setState({
                            val: e.target.value,
                        })
                    }}
                    value={this.state.val}
                /> <br/>
                <button onClick={() => { this.onDrawClick();
                }}>Draw</button>
                <button onClick={() => { this.onClearClick();
                }}>Clear</button>
            </div>
        );
    }
}

export default EdgeList;

