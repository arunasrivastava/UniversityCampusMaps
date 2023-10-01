import React, { Component } from "react";
import { Marker, Popup } from "react-leaflet";
import {
    UW_LATITUDE,
    UW_LATITUDE_OFFSET,
    UW_LATITUDE_SCALE,
    UW_LONGITUDE,
    UW_LONGITUDE_OFFSET,
    UW_LONGITUDE_SCALE
} from "./Constants";

interface MapPointProps {
    x1: number; // x coordinate of point
    y1: number; // y coordinate of point
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
 * A component that will render a point on the React Leaflet map for
 * point x1,y1. This point will convert from the assignment's coordinate
 * system (where 0,0 is the top-left of the UW campus) to latitude and
 * longitude, which the React Leaflet map uses
 */
class MapPoint extends Component<MapPointProps, {}> {
    constructor(props: any) {
        super(props);
        this.state = {
            edgeText: "",
        };
    }

    render() {
        return (
            // displays the point and the coordinates of the point when hovered over
            <Marker position={[yToLat(this.props.y1), xToLon(this.props.x1)]}>
                <Popup>
                    x: {this.props.x1} y: {this.props.y1} <br />
                </Popup>
            </Marker>
        )
    }
}

export default MapPoint;