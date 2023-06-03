import React, {Component, memo} from 'react';
import classnames from "classnames";
import {HeaderLeft, HeaderRight, HomeHeaderWrapper} from "./style";
import {NavLink} from "react-router-dom";
import {headerLinks} from "../../services/local-data";
import {Input} from "antd";
import {SearchOutlined} from '@ant-design/icons';

class HomeHeader extends Component {


    render() {
        const showItem = (item, index) => {
            return (
                <NavLink to={item.link}>
                    {item.title}
                    <i className="sprite_01 icon"></i>
                </NavLink>
            )
        }

        return (
            <HomeHeaderWrapper>
                <div className="wrap-v1 content">
                    <HeaderLeft>
                        <a className="logo sprite_01" href="#/">网易云音乐</a>
                        <div className="select-list">
                            {
                                headerLinks.map((item, index) => {
                                    return (
                                        <div className={classnames("select-item")} key={item.title}>
                                            {showItem(item, index)}
                                        </div>
                                    )
                                })
                            }
                        </div>
                    </HeaderLeft>
                    <HeaderRight>
                        <Input className="search" placeholder={"音乐"} prefix={<SearchOutlined/>}/>
                        <div className="center" onClick={this.connectDeveloper}>联系作者</div>
                    </HeaderRight>
                </div>
                <div className="divider"></div>
            </HomeHeaderWrapper>
        );
    }

    connectDeveloper() {
        console.log("联系作者")
    }
}

export default memo(HomeHeader);