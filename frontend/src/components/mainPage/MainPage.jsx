import React, {Component} from 'react'
import {StyleSheet, css} from 'aphrodite'
import {Layout, Menu} from 'antd';
import {observer,inject} from "mobx-react";
import {Link, NavLink} from "react-router-dom";
const { Header, Content} = Layout;


export default class MainPage extends Component {

    render() {
        let style = StyleSheet.create({
                menu: {
                    lineHeight: '64px'
                },
                wrapperContent:{
                    padding: '0 50px',
                    marginTop:40,
                    minHeight: 'calc(90% - 64px)'
                },
                layout:{
                    padding: '24px 0',
                    background: '#fff'
                },
                content:{
                    padding: '0 24px',
                    minHeight: 280,
                    minHeight: '70%'
                },
                logo:{
                    width: 120,
                    height: 31,
                    background: 'rgba(255,255,255,.2)',
                    margin: '16px 28px 16px 0',
                    float: 'left',
                    paddingTop:'14px',
                    textAlign:'center'
                },
                logoFont:{
                    color: 'white',
                    display: 'inline',
                    verticalAlign: 'center',
                    lineHeight:'0',
                }

            });
        return (
            <Layout>
                <Header className="header">
                    <div className={css(style.logo)}>
                        <h2 className={css(style.logoFont)}>Credit X</h2>
                    </div>
                    <Menu
                        theme="dark"
                        mode="horizontal"
                        defaultSelectedKeys={['1']}
                        className={css(style.menu)}
                    >
                        <Menu.Item key="1"><Link to="/">Listagem</Link></Menu.Item>
                        <Menu.Item key="2"><Link to="/cadastro">Cadastro</Link></Menu.Item>
                    </Menu>
                </Header>
                <Content className={css(style.wrapperContent)}>
                    <Layout className={css(style.layout)}>
                        <Content className={css(style.content)}>
                            {this.props.children}
                        </Content>
                    </Layout>
                </Content>
            </Layout>
        );
    }



}