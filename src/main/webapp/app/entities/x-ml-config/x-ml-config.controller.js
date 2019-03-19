(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('XMLConfigController', XMLConfigController);

    XMLConfigController.$inject = ['$scope', '$state', 'DataUtils', 'XMLConfig', 'XMLConfigSearch', '$q',
        'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile', 'DYMO'];

    function XMLConfigController($scope, $state, DataUtils, XMLConfig, XMLConfigSearch, $q,
                                 DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile, DYMO) {
        var vm = this;
        vm.title = 'Labels Configs';
        vm.entityClassHumanized = 'XML Config';
        vm.entityStateName = 'x-ml-config';

        vm.xMLConfigs = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                XMLConfig.query(function (result) {
                    vm.xMLConfigs = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.xMLConfigs, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('description').withTitle('Description'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '250px').notSortable().renderWith(actionsHtml)
        ];

        function yesNoHtml(data, type, full, meta) {
            if (full.byDefault) {
                return GUIUtils.getStatusTemplate('YES', 'success');
            } else {
                return GUIUtils.getStatusTemplate('NO', 'danger');
            }
        }

        function loadAll() {
            XMLConfig.query(function (result) {
                vm.xMLConfigs = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta) {
            return '<a class="btn-sm btn-primary" ng-click="vm.print(' + "'" + data.file + "'" + ",'PATIENT'"  + ')">' +
                '<i class="glyphicon glyphicon-print"></i></a>&nbsp;' + GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.print = print;
        function print(b64xml, type){
            if (type == 'PATIENT')
                DYMO.testPatientLabel(b64xml);
            else
                DYMO.testEnvironmentalLabel(b64xml);
        }
    }
})();
