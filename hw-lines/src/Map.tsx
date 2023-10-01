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



import { LatLngExpression } from "leaflet";
import React, { Component } from "react";
import { MapContainer, TileLayer } from "react-leaflet";
import "leaflet/dist/leaflet.css";
import MapLine from "./MapLine";
import { UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER } from "./Constants";

// This defines the location of the map. These are the coordinates of the UW Seattle campus
const position: LatLngExpression = [UW_LATITUDE_CENTER, UW_LONGITUDE_CENTER];

// Clears the input and updates the parent component's edge list
type  Edge = {
    x1: number;
    y1: number;
    x2: number;
    y2: number;
    color: string;
}
// Prop to receive a list of edges to be drawn on the map
interface MapProps {
    value: Edge[] | null;
}

interface MapState {
}
// Map is used to generate the visual map given MapProps and State
class Map extends Component<MapProps, MapState> {
    // No constructor needed, as it doesn't add any extra logic or state initialization
    // constructor(props: MapProps) {
    //     super(props);
    // }


    // Draw lines based on the recieved edges
    drawLine(val: Edge[] | null): JSX.Element[]{
        let result: JSX.Element[] = [];
        if (val === null){
            return result;
        }
        // Takes x1,y1,x2,y2, color, and key as props
        // The first four coordinates are the start and end of the lines
        // The key is an index value from val
        for(let i = 0; i < val.length; i++){
            let e = val[i];
            result.push(<MapLine key={i} color={e.color} x1={e.x1} y1={e.y1} x2={e.x2} y2={e.y2}/>);
        }
        return result;
    }
 // render function to draw prop values
  render() {

    return (
      <div id="map">
        <MapContainer
          center={position}
          zoom={15}
          scrollWheelZoom={false}
        >
          <TileLayer
            attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
            url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          />
          {
              this.drawLine(this.props.value)
          }
        </MapContainer>
      </div>
    );
  }
}

export default Map;

