(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AllOrdersDetailController', AllOrdersDetailController);

    AllOrdersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity',
        'AllOrders', 'TypeDosage', 'Facility', 'OrderComponent', 'Routes', '$q', 'DTColumnBuilder',
        'DTOptionsBuilder', 'GUIUtils', '$filter', '$state', '$compile', 'DateUtils', 'CoreService'];

    function AllOrdersDetailController($scope, $rootScope, $stateParams, previousState, entity,
                                       AllOrders, TypeDosage, Facility, OrderComponent, Routes, $q,
                                       DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $state, $compile, DateUtils, CoreService) {
        var vm = this;
        //Functions
        vm.changeEditingState = changeEditingState;
        vm.showOrderItemForm = showOrderItemForm;
        vm.editOrderItemForm = editOrderItemForm;
        vm.updateAllOrder = updateAllOrder;
        vm.saveOrderItems = saveOrderItems;

        //Variables
        vm.form = {};
        vm.allOrders = entity;
        vm.previousState = previousState.name;
        vm.typeDosage = TypeDosage.query();
        vm.routes = Routes.query();
        vm.editOrderComp = false;

        function showOrderItemForm() {
            vm.showItemForm = !vm.showItemForm;
            vm.orderComponents = {};
        }

        function changeEditingState() {
            vm.editing = !vm.editing;
        }

        function updateAllOrder () {
            vm.isSaving = true;
            vm.allOrders.facility = CoreService.getCurrentFacility();
            vm.allOrders.facility
            if (vm.allOrders.id !== null) {
                AllOrders.update(vm.allOrders, onSaveSuccess, onSaveError);
            } else {
                AllOrders.save(vm.allOrders, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:allOrdersUpdate', result);
            vm.isSaving = false;
            $state.go('all-orders', null, { reload: 'all-orders' });
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        //Order Components
        vm.orderComponents = [];
        vm.search = search;
        vm.dtOrderComponentInstance = {};

        vm.dtOrderComponentOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                vm.orderComponents = vm.allOrders.orderComponents;
                defer.resolve(vm.allOrders.orderComponents);
            }else {
                defer.resolve($filter('filter')(vm.orderComponents, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtOrderComponentColumns = [
            DTColumnBuilder.newColumn('days').withTitle('Days'),
            DTColumnBuilder.newColumn('medication').withTitle('Medication'),
            DTColumnBuilder.newColumn('dosage_form').withTitle('Dosage Form'),
            DTColumnBuilder.newColumn('dose').withTitle('Dose'),
            DTColumnBuilder.newColumn('administration_time').withTitle('Administration Time').renderWith(function (data, type, full) {
                return data.administration_time ? moment(data.administration_time).format('hh:mm:ss a') : "";
            }),
            //DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn('frequency').withTitle('Frequency'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];


        function actionsHtml(data, type, full, meta){
            return '<a class="btn-sm btn-warning" ng-click="vm.editOrderItemForm(' + data.id + ')">' +
                '<i class="fa fa-edit"></i></a>&nbsp;' +
                '<a class="btn-sm btn-danger" ' +
                'href="#/all-orders/' + vm.allOrders.id + '/order-component/' + data.id + '/delete">' +
                '<i class="fa fa-trash"></i></a>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function editOrderItemForm(id) {
            if(vm.allOrders.orderComponents.length == 1){
                vm.orderComponents = vm.allOrders.orderComponents[0];
                vm.orderComponents.administration_time = DateUtils.convertDateTimeFromServer(vm.orderComponents.administration_time);
            }else{
                for(var i =0; i < vm.allOrders.orderComponents.length; i++){
                    if(vm.allOrders.orderComponents[i].id == id){
                        vm.orderComponents = vm.allOrders.orderComponents[i];
                        vm.orderComponents.administration_time = DateUtils.convertDateTimeFromServer(vm.orderComponents.administration_time);
                    }
                }
            }
            vm.editOrderComp = true;
            vm.showItemForm = !vm.showItemForm;
        }

        function saveOrderItems() {
            if (vm.orderComponents) {
                if(!vm.editOrderComp){
                    vm.orderComponents.administration_time = DateUtils.convertDateTimeFromServer(vm.orderComponents.administration_time);
                    vm.orderComponents.status = 'ACT';
                    vm.allOrders.orderComponents.push(vm.orderComponents);
                }
                vm.editOrderComp = false;
                vm.dtOrderComponentInstance.reloadData();
                showOrderItemForm();
            }
        }
    }
})();
