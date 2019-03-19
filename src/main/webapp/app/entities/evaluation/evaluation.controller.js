(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationController', EvaluationController);

    EvaluationController.$inject = ['$scope', '$state', 'DataUtils', 'Evaluation', 'EvaluationSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function EvaluationController ($scope, $state, DataUtils, Evaluation, EvaluationSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.evaluations = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Evaluation.query(function(result) {
                  vm.evaluations = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.evaluations, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('enabled').withTitle('Enabled'),
              DTColumnBuilder.newColumn('alow_tech').withTitle('Alow_tech'),
              DTColumnBuilder.newColumn('patient_signature').withTitle('Patient_signature'),
              DTColumnBuilder.newColumn('one_perpatient').withTitle('One_perpatient'),
              //DTColumnBuilder.newColumn('billable').withTitle('Billable'),
              //DTColumnBuilder.newColumn('recurring').withTitle('Recurring'),
              //DTColumnBuilder.newColumn('daily_start_time').withTitle('Daily_start_time'),
              //DTColumnBuilder.newColumn('interval').withTitle('Interval'),
              //DTColumnBuilder.newColumn('show_daily').withTitle('Show_daily'),
              //DTColumnBuilder.newColumn('force_signature').withTitle('Force_signature'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Evaluation.query(function(result) {
                vm.evaluations = result;
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
