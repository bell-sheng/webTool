import MusicManger from "../pages/musics";
import MineManger from "../pages/mine";
import {Route, Routes} from "react-router-dom";

function Router() {
    return (
        <Routes>
            <Route path={"/"} element={<MusicManger/>}/>
            <Route path={"/mine"} element={<MineManger/>}/>
        </Routes>
    );
}

export default Router;