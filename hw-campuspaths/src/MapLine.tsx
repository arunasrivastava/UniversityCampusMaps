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
import { Polyline } from "react-leaflet";
import {
  UW_LATITUDE,
  UW_LATITUDE_OFFSET,
  UW_LATITUDE_SCALE,
  UW_LONGITUDE,
  UW_LONGITUDE_OFFSET,
  UW_LONGITUDE_SCALE
} from "./Constants";

interface MapLineProps {
  color: string; // color of line
  x1: number; // x coordinate of start point
  y1: number; // y coordinate of start point
  x2: number; // x coordinate of end point
  y2: number; // y coordinate of end point
}

/**
 * Converts x coordinate to longitude
 */
function xToLon(x: number): number {
  return UW_LONGITUDE + (x - UW_LONGITUDE_OFFSET) * UW_LONGITUDE_SCALE;
}

/**
 * Converts y coordinate to latitude
 */
function yToLat(y: number): number {
  return UW_LATITUDE + (y - UW_LATITUDE_OFFSET) * UW_LATITUDE_SCALE;
}

/**
 * A component that will render a line on the React Leaflet map of color from
 * point x1,y1 to x2,y2. This line will convert from the assignment's coordinate
 * system (where 0,0 is the top-left of the UW campus) to latitude and
 * longitude, which the React Leaflet map uses
 */
class MapLine extends Component<MapLineProps, {}> {
  constructor(props: any) {
    super(props);
    this.state = {
      edgeText: "",
    };
  }

  render() {
    return (
      <Polyline
        // Path options includes color, among a variety of line customizations
        pathOptions={{ color: this.props.color }}
        // Positions are a list of latitude,longitude pairs that consist of the
        // points on the line we draw on the map
        positions={[
          [yToLat(this.props.y1), xToLon(this.props.x1)],
          [yToLat(this.props.y2), xToLon(this.props.x2)],
        ]}
      />
    );
  }
}

export default MapLine;
