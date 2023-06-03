import React, {Component, memo} from 'react';
import {
    AppFooterWrapper,
    FooterLeft
} from "./style";

class HomeFooter extends Component {
    render() {
        return (
            <AppFooterWrapper>
                <div className="wrap-v2 content">
                    <FooterLeft className="left">
                        <div className="copyright">
                            <span>版权所有，翻版必究！</span>
                        </div>
                    </FooterLeft>
                </div>
            </AppFooterWrapper>
        );
    }
}

export default memo(HomeFooter);