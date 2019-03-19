(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .factory('Dashboard', Dashboard);

    Dashboard.$inject = ['$resource'];

    function Dashboard ($resource) {
        var service = $resource('api/dashboard-report/:fId', {}, {
            'dashboardReport': {method: 'GET', isArray: false},
            'unsignedForms': {url: 'api/unsigned-forms/:fId', isArray: true, method: 'GET'},
            'unsignedEvaluations': {url: 'api/unsigned-evaluations/:fId', isArray: true, method: 'GET'},
            'dConcurrentsReviewByDate': {url: 'api/dconcurrents-review-by-date/:fId/:date', isArray: true, method: 'GET'},
            'dConcurrentsReviewByDateVO': {url: 'api/dconcurrents-review-vo-by-date/:fId/:date', isArray: true, method: 'GET'},



            /** Tuning **/
            'physicianReview': {url: 'api/dashboard/physician-review/:fId', method: 'GET'},
            'unassignedLabResult': {url: 'api/dashboard/unassigned-lab-result/:fId', method: 'GET'},
            'unsignedFormsCount': {url: 'api/dashboard/unsigned-forms-count/:fId', method: 'GET'},
            'concurrentsReviewCount': {url: 'api/dashboard/concurrents-review-count/:fId', method: 'GET'},

            'groupSessionInProcessCount': {url: 'api/dashboard/group-session-in-process-count/:fId', method: 'GET'},
            'groupSessionPendingReviewCount': {url: 'api/dashboard/group-session-pending-review-count/:fId', method: 'GET'},
            'groupSessionCompletedCount': {url: 'api/dashboard/group-session-completed-count/:fId', method: 'GET'},
            'groupSessionTotalForTodayCount': {url: 'api/dashboard/group-session-total-for-today/:fId', method: 'GET'},

            'incomingPatients3DayNext': {url: 'api/dashboard/incoming-patients-3day-next/:fId', isArray: true, method: 'GET'},
            'dischargingPatients3DayNext': {url: 'api/dashboard/discharging-patients-3day-next/:fId', isArray: true, method: 'GET'},

            'currentVersion': {url: 'api/current-version', method: 'GET'},
            'getAllDashboardData': {url: 'api/dashboard/all-data/:fId', method: 'GET'},

        });

        return service;
    }
})();
