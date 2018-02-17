import React, {Component} from 'react'
import {Switch, BrowserRouter, Route, Router} from 'react-router-dom'
import {Provider} from "mobx-react";
import {mainStore} from "./stores/MainStore";
import "./assets/fonts/iconfont.css"
import MainPage from "./components/mainPage/MainPage";
import NewCreditRisk from "./components/newCreditRisk/NewCreditRisk";
import ListCreditRisk from "./components/listCreditRisk/ListCreditRisk";
import {listStore} from "./stores/ListStore";



const stores = {
    /*------ Initiate the Stores ------ */
    mainStore,
    listStore
}


export default class App extends Component {
    render() {
        return (
            <BrowserRouter>
                <MainPage>
                    <Provider {...stores}>
                        <Switch>
                            <Route exact path="/" component={ListCreditRisk} />
                            <Route  path="/cadastro" component={NewCreditRisk} />
                        </Switch>
                    </Provider>
                </MainPage>
            </BrowserRouter>
        )
    }
}
