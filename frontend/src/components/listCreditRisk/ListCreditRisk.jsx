import React, {Component} from 'react'
import {StyleSheet, css} from 'aphrodite'
import {Button, Row, Table} from 'antd';
import {observer,inject} from "mobx-react";
import "./ListCreditRisk.css";

@inject('listStore')
@observer
export default class ListCreditRisk extends Component {


    componentDidMount() {
        this.props.listStore.fetch();
    }
    render() {
        let listStore = this.props.listStore;
        const columns = [
            {
                title: 'Nome',
                dataIndex: 'name',
                sorter: true
            },
            {
                title: 'Limite de crédito',
                dataIndex: 'creditLimit',
                sorter: true
            },
            {
                title: 'Tipo de risco',
                dataIndex: 'riskType',
                sorter: true
            },
            {
                title: '% de juros',
                dataIndex: 'interestPercentage',
                sorter: true
            }
        ];
        const hasSelected = listStore.selectedRowKeys.length > 0;
        const rowSelection = {
            selectedRowKeys: listStore.selectedRowKeys,
            onChange: listStore.onSelectChange,
        };
        return (

            <Table
                columns={columns}
                rowKey={record => record.registered}
                dataSource={listStore.data}
                pagination={listStore.pagination}
                loading={listStore.loading}
                onChange={listStore.handleTableChange}
                rowSelection={rowSelection}
                bordered
                title={() => <Row>
                    <h2>Listagem dos clientes e limite de crédito</h2>
                    <Button
                        type="primary"
                        onClick={listStore.deleteCreditRisk}
                        disabled={!hasSelected}
                        loading={listStore.isDeleteBtnPressed}
                    >
                    Deletar
                    </Button>
                </Row>}
            />
        );
    }



}