(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GlucoseInterventionController', GlucoseInterventionController);

    GlucoseInterventionController.$inject = ['$scope', '$state', 'GlucoseIntervention', 'GlucoseInterventionSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function GlucoseInterventionController ($scope, $state, GlucoseIntervention, GlucoseInterventionSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Glucose Interventions';
        vm.entityClassHumanized = 'Glucose Intervention';
        vm.entityStateName = 'glucose-intervention';

        vm.glucoseInterventions = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                GlucoseIntervention.query(function(result) {
                    vm.glucoseInterventions = result;
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.glucoseInterventions, vm.searchQuery, undefined));
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
            GlucoseIntervention.query(function(result) {
                vm.glucoseInterventions = result;
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
