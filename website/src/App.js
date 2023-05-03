import React, {memo} from "react";

import {HashRouter} from "react-router-dom";

import "@/assets/css/base.css"
import HomeHeader from "./components/home-header";
import HomeFooter from "./components/home-footer";
import Router from "./router";

function App() {
    return (
        <HashRouter>
            <HomeHeader/>
            <Router/>
            <HomeFooter/>
        </HashRouter>
    );
}

export default memo(App);
