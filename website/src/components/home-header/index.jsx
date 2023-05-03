import React, {Component, memo} from 'react';
import {NavLink} from "react-router-dom";

class HomeHeader extends Component {
    render() {
        return (
            <div>
                <NavLink to="/">音乐库</NavLink>
                <NavLink to="/mine">我的音乐</NavLink>
            </div>
        );
    }
}

export default memo(HomeHeader);