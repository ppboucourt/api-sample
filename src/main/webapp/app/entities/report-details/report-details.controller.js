(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ReportDetailsController', ReportDetailsController);

    ReportDetailsController.$inject = ['$scope', '$state', 'ReportDetails', 'ReportDetailsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ReportDetailsController ($scope, $state, ReportDetails, ReportDetailsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.reportDetails = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  ReportDetails.query(function(result) {
                  vm.reportDetails = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.reportDetails, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('sorting').withTitle('Sorting'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            ReportDetails.query(function(result) {
                vm.reportDetails = result;
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
