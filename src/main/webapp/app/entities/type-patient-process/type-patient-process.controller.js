(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientProcessController', TypePatientProcessController);

    TypePatientProcessController.$inject = ['$scope', '$state', 'TypePatientProcess', 'TypePatientProcessSearch', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', 'CoreService'];

    function TypePatientProcessController ($scope, $state, TypePatientProcess, TypePatientProcessSearch, $q, DTColumnBuilder,
                                           DTOptionsBuilder, GUIUtils, $filter, CoreService ) {
        var vm = this;


        vm.typePatientProcesses = [];
        vm.search = search;
        // vm.loadAll = loadAll;
        vm.dtInstance = {};


        // loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                TypePatientProcess.byTypePage({pagId: 1, facId: CoreService.getCurrentFacility().id}, function (result) {
                    vm.typePatientProcesses = result;
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.typePatientProcesses, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('allow_ur').withTitle('Allow_ur'),
              DTColumnBuilder.newColumn('protect').withTitle('Protect'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        // function loadAll() {
        //     TypePatientProcess.query(function(result) {
        //         vm.typePatientProcesses = result;
        //         vm.searchQuery = null;
        //     });
        // }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
