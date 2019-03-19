(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeAdmissionStatusController', TypeAdmissionStatusController);

    TypeAdmissionStatusController.$inject = ['$scope', '$state', 'TypeAdmissionStatus', 'TypeAdmissionStatusSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function TypeAdmissionStatusController ($scope, $state, TypeAdmissionStatus, TypeAdmissionStatusSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.typeAdmissionStatuses = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  TypeAdmissionStatus.query(function(result) {
                  vm.typeAdmissionStatuses = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.typeAdmissionStatuses, vm.searchQuery, undefined));
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
            TypeAdmissionStatus.query(function(result) {
                vm.typeAdmissionStatuses = result;
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
