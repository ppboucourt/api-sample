(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabRequisitionsPatientController', LabRequisitionsPatientController);

    LabRequisitionsPatientController.$inject = ['$scope', '$state', 'LabRequisition', 'LabRequisitionSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function LabRequisitionsPatientController ($scope, $state, LabRequisition, LabRequisitionSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.labRequisitions = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  LabRequisition.query(function(result) {
                  vm.labRequisitions = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.labRequisitions, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('patient_signature').withTitle('Patient_signature'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
              DTColumnBuilder.newColumn('barcode').withTitle('Barcode'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            LabRequisition.query(function(result) {
                vm.labRequisitions = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
