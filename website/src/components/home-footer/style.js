import styled from 'styled-components';

export const AppFooterWrapper = styled.div`
  height: 72px;
  background-color: #f2f2f2;
  color: #666;
  border-top: 1px solid #d3d3d3;

  .content {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
`

export const FooterLeft = styled.div`
  padding-top: 15px;
  line-height: 24px;

  .link {
    a {
      color: #999;
    }

    .line {
      margin: 0 10px;
      color: #999;
    }
  }

  .copyright {
    span {
      margin-right: 15px;
    }
  }
`
