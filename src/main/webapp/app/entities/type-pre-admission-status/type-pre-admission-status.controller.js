(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePreAdmissionStatusController', TypePreAdmissionStatusController);

    TypePreAdmissionStatusController.$inject = ['$scope', '$state', 'TypePreAdmissionStatus', 'TypePreAdmissionStatusSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function TypePreAdmissionStatusController ($scope, $state, TypePreAdmissionStatus, TypePreAdmissionStatusSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.typePreAdmissionStatuses = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  TypePreAdmissionStatus.query(function(result) {
                  vm.typePreAdmissionStatuses = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.typePreAdmissionStatuses, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            TypePreAdmissionStatus.query(function(result) {
                vm.typePreAdmissionStatuses = result;
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
