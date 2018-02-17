import {action, observable, runInAction} from 'mobx'
import reqwest from 'reqwest';
import {message} from 'antd';

export default class ListStore{

    @observable
    loading = false;

    @observable
    selectedRowKeys= [];

    @observable
    selectedRows= [];

    @observable
    isDeleteBtnPressed = false;

    @observable
    pagination = {
        pageSize:5,
        defaultCurrent:0,
        current:0,
        size:0,
        total:0,
        page:1,
    };

    @observable
    data = [];

    @action.bound
    handleTableChange(pagination, filters, sorter){
        this.pagination.current = pagination.current;
        runInAction(() => this.fetch({
            sortField: sorter.field,
            sortOrder: sorter.order
        }));
    };

    @action.bound
    onSelectChange(selectedRowKeys,selectedRows){
        this.selectedRowKeys = selectedRowKeys;
        this.selectedRows = selectedRows.map( t => t.id);
    }

    @action.bound
    deleteCreditRisk(){
        this.isDeleteBtnPressed = true;
        reqwest({
            url: `http://localhost:8040/credit/risk?ids=${this.selectedRows.join(',')}`,
            method: 'delete',
            type: 'json',
        }).then((data) => {
            this.selectedRowKeys = [];
            this.isDeleteBtnPressed = false;
            message.success('Deletado com sucesso',3);
            runInAction(() => this.fetch());
        }).fail( () => {
            this.pagination.loading = false;
            message.error('Erro ao conectar no servidor',3);
        });
    }

    @action.bound
    fetch (sorter){
        this.loading = true;
        let page = this.pagination.current||0;
        let sortType;
        if (sorter){
            sortType =  sorter.sortOrder === "descend" ? 'desc' : 'asc';
        }
        let sort = sorter && sorter.sortOrder? `&sort=${sorter.sortField},${sortType}` : '';
        reqwest({
            url: `http://localhost:8040/credit/risk?page=${page ? page -1 : page}&size=${this.pagination.pageSize}${sort}`,
            method: 'get',
            type: 'json',
        }).then((data) => {
            this.pagination.size = data.size;
            this.pagination.total = data.totalElements;
            this.pagination.page = page;
            this.loading = false;

            this.data = data.content.map(t => {
                t.key = t.id;
                return t;
            });

        }).fail( () => {
            this.pagination.loading = false;
            message.error('Erro ao conectar no servidor',3);
        });
    }


}

export const listStore = new ListStore();