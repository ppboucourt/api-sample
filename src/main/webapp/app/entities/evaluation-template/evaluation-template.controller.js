(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationTemplateController', EvaluationTemplateController);

    EvaluationTemplateController.$inject = ['$scope', '$state', 'EvaluationTemplate', 'EvaluationTemplateSearch', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile', 'CoreService'];

    function EvaluationTemplateController ($scope, $state, EvaluationTemplate, EvaluationTemplateSearch, $q, DTColumnBuilder,
                                           DTOptionsBuilder, GUIUtils, $filter, $compile, CoreService ) {
        var vm = this;
        vm.title = 'Evaluation Templates';
        vm.entityClassHumanized = 'Evaluation Template';
        vm.entityStateName = 'evaluation-template';

        vm.evaluationTemplates = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                EvaluationTemplate.byFacility({id: CoreService.getCurrentFacility().id}, function(result) {
                    vm.evaluationTemplates = result;
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.evaluationTemplates, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('enabled').withTitle('Enabled').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn('patientSignature').withTitle('PatientSignature').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn('onlyOne').withTitle('OnlyOne').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn('billable').withTitle('Billable').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            EvaluationTemplate.query(function(result) {
                vm.evaluationTemplates = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['view', 'delete']);
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
