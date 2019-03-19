(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsController', GroupSessionDetailsController);

    GroupSessionDetailsController.$inject = ['$scope', '$state', 'GroupSessionDetails', 'GroupSessionDetailsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function GroupSessionDetailsController ($scope, $state, GroupSessionDetails, GroupSessionDetailsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;

        vm.groupSessionDetails = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  GroupSessionDetails.query(function(result) {
                  vm.groupSessionDetails = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.groupSessionDetails, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('groupLeader').withTitle('GroupLeader'),
              DTColumnBuilder.newColumn('start').withTitle('Start'),
              DTColumnBuilder.newColumn('finalized').withTitle('Finalized'),
              DTColumnBuilder.newColumn('topic').withTitle('Topic'),
              DTColumnBuilder.newColumn('review').withTitle('Review'),
              //DTColumnBuilder.newColumn('reviewDate').withTitle('ReviewDate'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            GroupSessionDetails.query(function(result) {
                vm.groupSessionDetails = result;
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
