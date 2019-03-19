(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FormsController', FormsController);

    FormsController.$inject = ['$scope', '$state', 'Forms', 'FormsSearch', '$q', 'DTColumnBuilder' ,
        'DTOptionsBuilder', 'GUIUtils', '$filter', 'CoreService', 'TypeFormRules'];

    function FormsController ($scope, $state, Forms, FormsSearch, $q, DTColumnBuilder,
                              DTOptionsBuilder, GUIUtils, $filter, CoreService, TypeFormRules ) {
        var vm = this;

        //Functions
        vm.search = search;
        vm.loadAll = loadAll;
        vm.changeEditingState = changeEditingState;

        //Variable
        vm.forms = [];
        vm.dtInstance = {};
        vm.editing = false;

        vm.title = 'Forms';
        vm.entityClassHumanized = 'Form';
        vm.entityStateName = 'forms';

        var facility = CoreService.getCurrentFacility();

        //loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Forms.queryByFacility({id: facility.id}, function(result) {
                    vm.forms = result;
                    defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.forms, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('loadManually').withTitle('Load Manually').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn('patientSignatureRequired').withTitle('Patient Signature').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn('guarantorSignatureRequired').withTitle('Guarantor Signature').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn('allowAttachment').withTitle('Allow Attachment').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            DTColumnBuilder.newColumn('only_one_perpatient').withTitle('OTO').renderWith(function (data, type, full) {
                return GUIUtils.colorHtmlYesNo(data);
            }),
            //DTColumnBuilder.newColumn('allow_revocation').withTitle('Allow_revocation'),
            //DTColumnBuilder.newColumn('form_content').withTitle('Form_content'),
            //DTColumnBuilder.newColumn('status').withTitle('Status'),
            //DTColumnBuilder.newColumn('enabled').withTitle('Enabled'),
            //DTColumnBuilder.newColumn('required_lab_requisitions').withTitle('Required_lab_requisitions'),
            //DTColumnBuilder.newColumn('expires_days').withTitle('Expires_days'),
            //DTColumnBuilder.newColumn('form_experies').withTitle('Form_experies'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            Forms.queryByFacility({id: facility.id},function(result) {
                vm.forms = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data){
            return '<a class="btn-sm btn-primary" ui-sref="forms-detail({id:' + data.id + '})" href="#/forms/' + data.id + '">' +
                '   <i class="fa fa-eye"></i></a>&nbsp;' +
                '<a class="btn-sm btn-danger" ui-sref="forms.delete({id:' + data.id + '})"  href="#/forms/' + data.id + '/delete">' +
                '   <i class="fa fa-trash"></i></a>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function changeEditingState() {
            vm.editing = !vm.editing;
        }

    }
})();
