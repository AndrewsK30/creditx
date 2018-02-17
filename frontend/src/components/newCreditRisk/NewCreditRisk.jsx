import React, {Component} from 'react'
import {StyleSheet, css} from 'aphrodite'
import {Button, message, Form, Input, InputNumber, Row, Select} from 'antd';
import {observer,inject} from "mobx-react";
const FormItem = Form.Item;

@inject('mainStore')
@observer
export class NewCreditRisk extends Component {

    handleSubmit = (e) => {
        e.preventDefault();
        const { resetFields } = this.props.form;
        this.props.form.validateFieldsAndScroll((err, values) => {
            if (!err) {
                this.props.mainStore.saveNewCreditRisk(values)
                .then(() => {
                    message.success('Cadastro enviado com sucesso',3);
                    resetFields();
                }).fail(() => message.error('Erro ao conectar no servidor',3));
            }
        });
    };

    render() {
        let style = StyleSheet.create({
            title:{
                textAlign: 'center',
                marginBottom: 40
            }
        });
        const formItemLayout = {
            labelCol: {
                xs: { span: 24 },
                sm: { span: 8 },
            },
            wrapperCol: {
                xs: { span: 9 },
                sm: { span: 9 },
            },
        };
        const formItemButton = {
            labelCol: {
                xs: { span: 10, offset:10 },
                sm: { span: 8 },
            },
            wrapperCol: {
                xs: { span: 12, offset:11 },
                sm: { span: 8 },
            },
        };
        const { getFieldDecorator } = this.props.form;
        return (
        <div>
            <h2 className={css(style.title)}>Cadastre um novo cliente</h2>
            <Form onSubmit={this.handleSubmit}>
                <FormItem
                    {...formItemLayout}
                    label="Nome"
                >
                    {getFieldDecorator('name', {
                        rules: [{
                            required: true, message: 'Por favor preencha o seu nome!',
                        }],
                    })(
                        <Input />
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Limite de crédito"
                >
                    {getFieldDecorator('creditLimit', {
                        rules: [
                        {
                            required: true, message: 'Por favor preencha o limite de crédito!',
                        },
                        {
                            type: 'number',min: 0.0, message: 'Valores maiores que 0!',
                        }],
                    })(
                        <InputNumber min={0}/>
                    )}
                </FormItem>
                <FormItem
                    {...formItemLayout}
                    label="Tipo de risco"
                    hasFeedback
                >
                    {getFieldDecorator('riskType', {
                        rules: [
                            { required: true, message: 'Por favor selecione um tipo de risco!' },
                        ],
                    })(
                        <Select placeholder="Por favor selecione um tipo de risco">
                            <Option value="A">A</Option>
                            <Option value="B">B</Option>
                            <Option value="C">C</Option>
                        </Select>
                    )}
                </FormItem>
                <FormItem {...formItemButton}>
                    <Button type="primary" size="large" htmlType="submit">Cadastrar</Button>
                </FormItem>
            </Form>
        </div>
        );
    }
}

export default Form.create()(NewCreditRisk);