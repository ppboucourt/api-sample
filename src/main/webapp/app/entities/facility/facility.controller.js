(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FacilityController', FacilityController);

    FacilityController.$inject = ['$scope', '$state', 'DataUtils', 'Facility', 'FacilitySearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder',
        'GUIUtils', '$filter', '$compile', 'CoreService'];

    function FacilityController ($scope, $state, DataUtils, Facility, FacilitySearch, $q, DTColumnBuilder, DTOptionsBuilder,
                                 GUIUtils, $filter, $compile, CoreService ) {
        var vm = this;
        vm.title = 'Facilities';
        vm.entityClassHumanized = 'Facility';
        vm.entityStateName = 'facility';

        vm.facilities = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Facility.query(function(result) {
                    vm.facilities = result;
                    if(!CoreService.getCurrentFacility())
                        CoreService.setCurrentFacility(result[0]);
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.facilities, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('street').withTitle('Street'),
            DTColumnBuilder.newColumn('city').withTitle('City'),
            DTColumnBuilder.newColumn('state').withTitle('State'),
            //DTColumnBuilder.newColumn('zip').withTitle('Zip'),
            //DTColumnBuilder.newColumn('county').withTitle('County'),
            //DTColumnBuilder.newColumn('phone').withTitle('Phone'),
            //DTColumnBuilder.newColumn('fax').withTitle('Fax'),
            //DTColumnBuilder.newColumn('website').withTitle('Website'),
            //DTColumnBuilder.newColumn('logo').withTitle('Logo'),
            //DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            // Facility.query(function(result) {
            //     vm.facilities = result;
            //     vm.searchQuery = null;
            // });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['update', 'delete']);
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
