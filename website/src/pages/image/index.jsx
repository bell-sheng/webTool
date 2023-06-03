import React, {Component, memo} from 'react';
import {Button, Col, Form, Image, Input, InputNumber, Layout, Row, Select, Spin} from 'antd';

class ImageManger extends Component {
    constructor(props) {
        super(props);
        this.state = {
            generateImages: [],
            isLoading: false,
        }
    }


    onFinish = async (values) => {
        console.log(values);
        this.setState({
            isLoading: true,
        })
        const {Configuration, OpenAIApi} = require("openai");
        const configuration = new Configuration({
            apiKey: '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',
        });
        const openai = new OpenAIApi(configuration);
        const response = await openai.createImage({
            ...values
        });
        console.log(response);
        this.setState({
            generateImages: response.data.data,
            isLoading: false,
        })
    };

    render() {
        const {Option} = Select;
        const layout = {
            labelCol: {
                span: 8,
            },
            wrapperCol: {
                span: 16,
            },
        };
        const validateMessages = {
            required: '${label} is required!',
            types: {
                email: '${label} is not a valid email!',
                number: '${label} is not a valid number!',
            },
            number: {
                range: '${label} must be between ${min} and ${max}',
            },
        };


        return (
            <div>
                <Row>
                    <Col span={12}>
                        <Form
                            {...layout}
                            name="nest-messages"
                            onFinish={this.onFinish}
                            style={{
                                padding: 10,
                                maxWidth: 400,
                            }}
                            validateMessages={validateMessages}
                        >
                            <Form.Item
                                name={['n']}
                                label="生成图片数量"
                                rules={[
                                    {
                                        required: true,
                                        type: 'number',
                                        min: 1,
                                        max: 10,
                                    },
                                ]}
                            >
                                <InputNumber/>
                            </Form.Item>
                            <Form.Item name={['size']} label="生成图片大小"
                                       rules={[
                                           {
                                               required: true,
                                               type: 'enum',
                                               enum: ['256x256', '512x512', '1024x1024']
                                           },
                                       ]}>
                                <Select placeholder="选择一个图片大小">
                                    <Option value="256x256">256x256</Option>
                                    <Option value="512x512">512x512</Option>
                                    <Option value="1024x1024">1024x1024</Option>
                                </Select>
                            </Form.Item>
                            <Form.Item name={['prompt']} label="文本描述"
                                       rules={[
                                           {
                                               required: true
                                           },
                                       ]}>
                                <Input.TextArea/>
                            </Form.Item>
                            <Form.Item
                                wrapperCol={{
                                    ...layout.wrapperCol,
                                    offset: 8,
                                }}
                            >
                                <Button type="primary" htmlType="submit">
                                    提交
                                </Button>
                            </Form.Item>
                        </Form>
                    </Col>
                    <Spin tip="Loading..." spinning={this.state.isLoading}>
                        <Col span={12}>
                            <div>
                                {
                                    this.state.generateImages.map((value) => {
                                        return <Image width={200} src={value.url}/>
                                    })
                                }
                            </div>
                        </Col>
                    </Spin>
                </Row>
            </div>
        );
    }
}

export default memo(ImageManger);