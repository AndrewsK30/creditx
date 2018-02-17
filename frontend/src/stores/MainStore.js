import {action} from 'mobx'
import reqwest from 'reqwest';
import {message} from 'antd';

export default class MainStore{

    @action
    saveNewCreditRisk(params) {
        return reqwest({
            url: `http://localhost:8040/credit/risk`,
            method: 'post',
            type: 'json',
            contentType: 'application/json',
            crossOrigin: true,
            type: 'json',
            data:JSON.stringify(params)
        });

    }


}

export const mainStore = new MainStore();