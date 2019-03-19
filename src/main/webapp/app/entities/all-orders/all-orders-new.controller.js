(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AllOrdersNewController', AllOrdersNewController);

    AllOrdersNewController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'AllOrders',
        'TypeDosage', 'Facility', 'OrderComponent', 'DTOptionsBuilder', 'DTColumnBuilder', '$q', 'Routes', '$compile',
        'DateUtils', '$state', '$sessionStorage', '$uibModal'];

    function AllOrdersNewController ($timeout, $scope, $stateParams, entity, AllOrders,
                                     TypeDosage, Facility, OrderComponent, DTOptionsBuilder, DTColumnBuilder, $q, Routes, $compile,
                                     DateUtils, $state, $sessionStorage, $uibModal) {
        var vm = this;
        //Functions
        vm.showOrderItemForm = showOrderItemForm;
        vm.saveOrderItems = saveOrderItems;
        vm.save = save;
        vm.deleteNewOrderComponent = deleteNewOrderComponent;
        vm.editOrderItemForm = editOrderItemForm;

        //Variables
        vm.form = {};
        vm.allOrders = entity;
        vm.typedosages = TypeDosage.query();
        vm.facilities = Facility.query();
        // vm.ordercomponents = OrderComponent.query();
        vm.typeDosage = TypeDosage.query();
        vm.routes = Routes.query();
        $sessionStorage.orderComponentList = [];
        vm.editOrderComp = false;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
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

        function showOrderItemForm() {
            vm.showItemForm = !vm.showItemForm;
            vm.orderComponents = {
                days: null,
                medication: null,
                dosage_form: null,
                dose: null,
                administration_time: null,
                status: 'ACT',
                frequency: null,
                id: null,
                count: null
            };
        }

        //Order Components
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
            DTColumnBuilder.newColumn(null).withTitle('Administration Time').renderWith(function (data, type, full) {
                return data.administration_time ? moment(data.administration_time).format('hh:mm:ss a') : "";
            }),
            //DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn('frequency').withTitle('Frequency'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];


        function actionsHtml(data, type, full, meta){
            return '<a class="btn-sm btn-warning" ng-click="vm.editOrderItemForm(' + data.count + ')">' +
                '<i class="fa fa-edit"></i></a>&nbsp;' +
                '<a class="btn-sm btn-danger" ng-click="vm.deleteNewOrderComponent(' + data.count + ')">' +
                '<i class="fa fa-trash"></i></a>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function editOrderItemForm(count) {
            if(vm.allOrders.orderComponents.length == 1){
                vm.orderComponents = vm.allOrders.orderComponents[0];
                vm.orderComponents.administration_time = DateUtils.convertDateTimeFromServer(vm.orderComponents.administration_time);
            }else{
                for(var i =0; i < vm.allOrders.orderComponents.length; i++){
                    if(vm.allOrders.orderComponents[i].count == count){
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
                    vm.orderComponents.count = $sessionStorage.orderComponentList.length;
                    $sessionStorage.orderComponentList.push(vm.orderComponents);
                }
                vm.editOrderComp = false;
                vm.allOrders.orderComponents = $sessionStorage.orderComponentList;
                vm.dtOrderComponentInstance.reloadData();
                showOrderItemForm();
            }
        }

        var modalInstance = null;

        function deleteNewOrderComponent(count) {
                if (modalInstance !== null) return;
                modalInstance = $uibModal.open({
                    animation: true,
                    backdrop: 'static',
                    keyboard: false,
                    templateUrl: 'app/entities/order-component/order-component-delete-dialog.html',
                    controller: 'OrderComponentNewDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        count: count
                    }
                });
                modalInstance.result.then(
                    resetModalDeleteComponent,
                    resetModalDeleteComponent
                );
        }

        var resetModalDeleteComponent = function () {
            modalInstance = null;
            vm.allOrders.orderComponents = $sessionStorage.orderComponentList;
            vm.dtOrderComponentInstance.reloadData();
            // $sessionStorage.orderComponentList = null;
        };
    }
})();
