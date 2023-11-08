import MusicManger from "../pages/musics";
import MineManger from "../pages/mine";
import ImageManger from "../pages/image";
import {Route, Routes} from "react-router-dom";
import TaskToDo from "../pages/todo";

function Router() {
    return (
        <Routes>
            <Route path={"/"} element={<ImageManger/>}/>
            <Route path={"/mine"} element={<MineManger/>}/>
            <Route path={"/image"} element={<ImageManger/>}/>
            <Route path={"/task"} element={<TaskToDo/>}/>
        </Routes>
    );
}

export default Router;