import React, {Component, memo} from 'react';
import {InboxOutlined} from '@ant-design/icons';

import {Input, message, Space, Upload} from 'antd';

class TaskToDo extends Component {

    constructor(props) {
        super(props);
        this.state = {
            needYear: 2024,
            needMonth: 5,
        }
    }

    render() {
        const {Dragger} = Upload;
        const props = {
            name: 'file',
            multiple: true,
            action: 'http://localhost:8090/v1/api/attendance/statistics',
            onChange(info) {
                const {status} = info.file;
                if (status !== 'uploading') {
                    console.log(info.file, info.fileList);
                }
                if (status === 'done') {
                    message.success(`${info.file.name} file uploaded successfully.`);
                } else if (status === 'error') {
                    message.error(`${info.file.name} file upload failed.`);
                }
            },
            onDrop(e) {
                console.log('Dropped files', e.dataTransfer.files);
            },
        };
        const body = {
            needYear: this.state.needYear,
            needMonth: this.state.needMonth,
        }
        return (
            <div>
                <Space.Compact>
                    <Input
                        style={{
                            width: '20%',
                        }}
                        defaultValue="年份"
                    />
                    <Input
                        style={{
                            width: '80%',
                        }}
                        onChange={(obj) =>
                            this.needYear = obj.target.value
                        }
                        value={this.state.needYear}
                    />
                </Space.Compact>
                <Space.Compact>
                    <Input
                        style={{
                            width: '20%',
                        }}
                        defaultValue="月份"
                    />
                    <Input
                        style={{
                            width: '80%',
                        }}
                        onChange={(obj) => {
                            console.log(obj)
                            this.setState({
                                needMonth: obj.target.value,
                            })
                        }
                        }
                        value={this.state.needMonth}
                    />
                </Space.Compact>
                <Dragger {...props} data={body}>
                    <p className="ant-upload-drag-icon">
                        <InboxOutlined/>
                    </p>
                    <p className="ant-upload-text">Click or drag file to this area to upload</p>
                    <p className="ant-upload-hint">
                        Support for a single or bulk upload. Strictly prohibited from uploading company data or other
                        banned files.
                    </p>
                </Dragger>
            </div>
        );
    }
}

export default memo(TaskToDo);