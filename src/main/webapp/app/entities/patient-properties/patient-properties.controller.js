(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Patient_propertiesController', Patient_propertiesController);

    Patient_propertiesController.$inject = ['$scope', '$state', 'DataUtils', 'Patient_properties', 'Patient_propertiesSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function Patient_propertiesController ($scope, $state, DataUtils, Patient_properties, Patient_propertiesSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.patient_properties = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Patient_properties.query(function(result) {
                  vm.patient_properties = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.patient_properties, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('picture').withTitle('Picture'),
              DTColumnBuilder.newColumn('description').withTitle('Description'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Patient_properties.query(function(result) {
                vm.patient_properties = result;
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
