import MusicManger from "../pages/musics";
import MineManger from "../pages/mine";
import ImageManger from "../pages/image";
import {Route, Routes} from "react-router-dom";

function Router() {
    return (
        <Routes>
            <Route path={"/"} element={<MusicManger/>}/>
            <Route path={"/mine"} element={<MineManger/>}/>
            <Route path={"/image"} element={<ImageManger/>}/>
        </Routes>
    );
}

export default Router;