(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceLevelController', InsuranceLevelController);

    InsuranceLevelController.$inject = ['$scope', '$state', 'InsuranceLevel', 'InsuranceLevelSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function InsuranceLevelController ($scope, $state, InsuranceLevel, InsuranceLevelSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Insurance Levels';
        vm.insuranceLevels = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  InsuranceLevel.query(function(result) {
                  vm.insuranceLevels = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.insuranceLevels, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            InsuranceLevel.query(function(result) {
                vm.insuranceLevels = result;
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
